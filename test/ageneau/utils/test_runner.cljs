(ns ageneau.utils.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [ageneau.utils.core-test]))

(doo-tests 'ageneau.utils.core-test)
