TARGET_DIR=target
EXCLUDE_FROM_JAR='[]'

include build/common/common.mk

test-clj:
	$(CLOJURE) -M:test:kaocha-runner unit-clj
test-cljs:
	$(CLOJURE) -M:test:kaocha-runner unit-cljs
test-karma:
	$(NPX) shadow-cljs -A:test compile ci
	$(NPX) karma start --single-run

test: test-clj test-karma

clean:
	@rm -rf target
