(ns revoir.cli
  (:require [revoir.core :as r]
            [clojure.tools.cli :as cli]
            [clojure.string :as str])
  (:gen-class))

(def cli-options
  [["-i" "--input INPUTFILE" "Input file"
    :parse-fn str
    :validate [#(some? %) "An input file must be provided"]]
   ["-o" "--output OUTPUTFILE" "Output file name"
    :parse-fn str
    :validate [#(some? %) "An output file name must be provided"]]

   ["-h" "--help"]])

(defn- error-msg [errors]
  (str "The following errors occured while parsing the command:\n\n"
       (str/join "\n" errors)))

(defn- exit [status msg]
  (println msg)
  (System/exit status))

(defn- load-input-file [filename]
  (try
    {:error nil :results (r/read-file filename)}
    (catch Exception _e
      (exit 1 "Error loading input file"))))

(defn- write-output-file [contents filename]
  (try
    {:error nil :results (r/write-file contents filename)}
    (catch Exception _e
      (exit 1 "Error writing outputs"))))

(defn- transform-and-parse [contents]
  (try
    (r/transform (r/parse contents))
    (catch Exception _e
      (exit 1 "Parsing failure"))))

(defn- handler [input-filename output-filename]
  (some-> input-filename
          load-input-file
          (:results)
          transform-and-parse
          (write-output-file output-filename)
          (:results)))

(defn -main [& args]
  (let [{:keys [options errors] :as x} (cli/parse-opts args cli-options)]
    (cond errors
          (exit 1 (error-msg errors))

          (nil? (:input options))
          (exit 1
                (str "Missing required argument: Input\n"
                     "-i <input> --input <input>"))

          (nil? (:output options))
          (exit 1
                (str "Missing required argument: Output\n"
                     "-i <output> --input <output>"))

          (:help options)
          (exit 0 "vamo ver")

          :else
          (handler (:input options)
                   (:output options)))))

(comment
  (-> "resources/thing.asm"
      r/read-file
      r/parse
      (r/write-file "testando")))
