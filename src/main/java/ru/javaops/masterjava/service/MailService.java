package ru.javaops.masterjava.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MailService {
    private static final String OK = "OK";

    private static final String INTERRUPTED_BY_FAULTS_NUMBER = "+++ Interrupted by faults number";
    private static final String INTERRUPTED_BY_TIMEOUT = "+++ Interrupted by timeout";
    private static final String INTERRUPTED_EXCEPTION = "+++ InterruptedException";

    private final ExecutorService mailExecutor = Executors.newFixedThreadPool(8);

    public GroupResult sendToList(final String template, final Set<String> emails) throws Exception {
        final CompletionService<MailResult> completionService = new ExecutorCompletionService<>(mailExecutor);

        List<Future<MailResult>> futures = emails.stream()
                .map(email -> completionService.submit(() -> sendToUser(template, email)))
                .collect(Collectors.toList());

        return new Callable<GroupResult>() {
            private int success = 0;
            private List<MailResult> failed = new ArrayList<>();

            @Override
            public GroupResult call() {
                while (!futures.isEmpty()) {
                    try {
                        Future<MailResult> future = completionService.poll(10, TimeUnit.SECONDS);
                        if (future == null) {
                            return cancelWithFail(INTERRUPTED_BY_TIMEOUT);
                        }
                        futures.remove(future);
                        MailResult mailResult = future.get();
                        if (mailResult.isOk()) {
                            success++;
                        } else {
                            failed.add(mailResult);
                            if (failed.size() >= 5) {
                                return cancelWithFail(INTERRUPTED_BY_FAULTS_NUMBER);
                            }
                        }
                    } catch (ExecutionException e) {
                        return cancelWithFail(e.getCause().toString());
                    } catch (InterruptedException e) {
                        return cancelWithFail(INTERRUPTED_EXCEPTION);
                    }
                }
/*
                for (Future<MailResult> future : futures) {
                    MailResult mailResult;
                    try {
                        mailResult = future.get(10, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        return cancelWithFail(INTERRUPTED_EXCEPTION);
                    } catch (ExecutionException e) {
                        return cancelWithFail(e.getCause().toString());
                    } catch (TimeoutException e) {
                        return cancelWithFail(INTERRUPTED_BY_TIMEOUT);
                    }
                    if (mailResult.isOk()) {
                        success++;
                    } else {
                        failed.add(mailResult);
                        if (failed.size() >= 5) {
                            return cancelWithFail(INTERRUPTED_BY_FAULTS_NUMBER);
                        }
                    }
                }
*/
                return new GroupResult(success, failed, null);
            }

            private GroupResult cancelWithFail(String cause) {
                futures.forEach(f -> f.cancel(true));
                return new GroupResult(success, failed, cause);
            }
        }.call();
    }

    // dummy realization
    public MailResult sendToUser(String template, String email) throws Exception {
        try {
            Thread.sleep(500);  //delay
        } catch (InterruptedException e) {
            // log cancel;
            return null;
        }
        return Math.random() < 0.7 ? MailResult.ok(email) : MailResult.error(email, "Error");
    }

    public static class MailResult {
        private final String email;
        private final String result;

        private static MailResult ok(String email) {
            return new MailResult(email, OK);
        }

        private static MailResult error(String email, String error) {
            return new MailResult(email, error);
        }

        public boolean isOk() {
            return OK.equals(result);
        }

        private MailResult(String email, String cause) {
            this.email = email;
            this.result = cause;
        }

        @Override
        public String toString() {
            return '(' + email + ',' + result + ')';
        }
    }

    public static class GroupResult {
        private final int success; // number of successfully sent email
        private final List<MailResult> failed; // failed emails with causes
        private final String failedCause;  // global fail cause

        public GroupResult(int success, List<MailResult> failed, String failedCause) {
            this.success = success;
            this.failed = failed;
            this.failedCause = failedCause;
        }

        @Override
        public String toString() {
            return "Success: " + success + '\n' +
                    "Failed: " + failed.toString() + '\n' +
                    (failedCause == null ? "" : "Failed cause" + failedCause);
        }
    }
}