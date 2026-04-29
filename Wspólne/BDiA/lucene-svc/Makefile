.PHONY: kaocha lint format clean

kaocha:
	@clojure -M:kaocha

lint:
	clj-kondo --lint src/main/clj/
	kibit
	splint src/main/clj/

format:
	@standard-clojure-style-js fix src/main/clj/

clean:
	@rm -rf .cpcache/ target/
