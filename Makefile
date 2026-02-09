.PHONY: check bench quality_gate clean

# Tier 2 Tripwire: Fails if any function has complexity > 10 (Rank C)
check:
	@echo "🔍 Running Quality & Complexity Tripwire..."
	@# Ruff: Fast Linting
	ruff check .
	@# Xenon: Fails build if Cyclomatic Complexity is too high (Tier 2 Breach)
	xenon --max-absolute C --max-modules B --max-average A .

# Tier 1 Tripwire: Fails if code is >5% slower than baseline
bench:
	@echo "⏱️ Running Performance Tripwire..."
	@# Run benchmarks, fail if 1.05x slower than saved baseline
	pytest benchmarks/ --benchmark-only --benchmark-compare --benchmark-fail-if-ratio 1.05

# The Master Switch (Used by GitHub Actions)
quality_gate: check bench

	@echo "✅ Quality Gate Passed: Code is Simple and Fast."

clean:
	rm -rf .pytest_cache .ruff_cache .benchmarks profile_results.prof
