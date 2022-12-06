package pairmatching.controller;

import pairmatching.view.OutputView;

public class MenuController {
    private enum Command {
        PAIR_MATCHING("1"),
        PAIR_SEARCHING("2"),
        PAIR_INITIATE("3"),
        QUIT_APPLICATION("Q");

        private final String command;

        Command(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    private final MatchingController matchingController = new MatchingController();
    private boolean isApplicationRun = true;

    public void run() {
        do {
            String choice = matchingController.run();
            runPairMatching(choice);
            searchPairMatching(choice);
            initPairMatching(choice);
            quitPairMatching(choice);
        } while (isApplicationRun);
    }

    private void runPairMatching(String choice) {
        if (choice.equals(Command.PAIR_MATCHING.toString())) {
            try {
                matchingController.executePairMatching();
            } catch (IllegalStateException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void searchPairMatching(String choice) {
        if (choice.equals(Command.PAIR_SEARCHING.toString())) {
            matchingController.searchPairMatching();
        }
    }

    private void initPairMatching(String choice) {
        if (choice.equals(Command.PAIR_INITIATE.toString())) {
            matchingController.initPairMatching();
        }
    }

    private void quitPairMatching(String choice) {
        if (choice.equals(Command.QUIT_APPLICATION.toString())) {
            isApplicationRun = false;
        }
    }
}