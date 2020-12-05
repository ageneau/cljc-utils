TARGET_DIR=target
EXCLUDE_FROM_JAR='[]'

include build/common/common.mk

test-clj:
	$(CLOJURE) -M:test:kaocha-runner unit-clj
test-cljs:
	$(CLOJURE) -M:test:kaocha-runner unit-cljs

test: test-clj test-cljs

clean:
	@rm -rf target
