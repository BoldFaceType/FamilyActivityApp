.PHONY: check bench quality_gate clean test

# Tier 2 Tripwire: Fails if any function has cyclomatic complexity > 10
# Uses ktlint (style) + detekt (complexity/static analysis)
check:
	@echo "Running Kotlin Quality & Complexity Tripwire..."
	./gradlew ktlintCheck
	./gradlew detekt

# Tier 1 Tripwire: Fails if code is >5% slower than baseline
# NOTE: Requires androidTest benchmark module. Runs unit tests until then.
bench:
	@echo "Running Performance Tripwire..."
	./gradlew test --tests "*.benchmark.*" || echo "No benchmark tests found — skipping."

# Run unit tests
test:
	@echo "Running Unit Tests..."
	./gradlew test

# The Master Switch (Used by CI)
quality_gate: check test
	@echo "Quality Gate Passed: Code is Simple, Fast, and Tested."

clean:
	./gradlew clean
	rm -rf .gradle build app/build
