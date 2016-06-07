# Selenium / PhantomJS / Maven POC
[![Build Status](https://travis-ci.org/davidkey/selenium-phantomjs-maven-poc.svg?branch=master)](https://travis-ci.org/davidkey/selenium-phantomjs-maven-poc)
### What is this ? ###
A tiny POC showing a "pure" maven / selenium / phantomjs test. The sample test simply navigates to github and takes a screenshot.

### How do I run it? ###

```sh
mvn install test # first time requires an install goal to download phantomjs binary
mvn test # subsequent runs can simply run test goal
```


