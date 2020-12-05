ifeq ($(OS),Windows_NT)     # is Windows_NT on XP, 2000, 7, Vista, 10...
    detected_OS := Windows
else
    detected_OS := $(shell uname)  # same as "uname -s"
endif

ifeq ($(detected_OS),Windows)
OPEN=cmd /c start
else ifeq ($(detected_OS),Darwin)
OPEN=open
else
OPEN=xdg-open
endif

ifeq ($(detected_OS),Windows)
KARMA=cmd /c karma
GIT=git.exe
CLOJURE=clojure.exe
else
KARMA=karma
GIT=git
CLOJURE=clojure
endif

ifndef TARGET_DIR
$(error TARGET_DIR is undefined)
endif
ifndef EXCLUDE_FROM_JAR
$(error EXCLUDE_FROM_JAR is undefined)
endif


POM_FILE=pom.xml
VERSION_FILE=$(TARGET_DIR)/meta.json
PROJECT_INFO=$(TARGET_DIR)/project-info.json
DEPS_EDN=deps.edn
NPX=npx
UPDATE_README=./bin/update-readme
README=README.md
WATCH=$(NPX) watch
DEPLOY=./bin/deploy
BUILD_JAR=./bin/build-jar
LOCAL_INSTALL=./bin/local-install
POM=./bin/pom

$(PROJECT_INFO): $(POM_FILE)
	test -d $(TARGET_DIR) || mkdir $(TARGET_DIR)
	$(POM) json > $(PROJECT_INFO)

update-readme: $(PROJECT_INFO)
	$(UPDATE_README) $(README)

local-install: $(PROJECT_INFO)
	$(LOCAL_INSTALL)

build-jar: $(PROJECT_INFO) $(CSS_FILES)
	$(BUILD_JAR) :exclude $(EXCLUDE_FROM_JAR)

deploy: $(POM_FILE) $(PROJECT_INFO)
	$(DEPLOY)

check-git-clean:
	$(GIT) diff-index --quiet HEAD

.PHONY: update-readme local-install build-jar deploy check-git-clean
