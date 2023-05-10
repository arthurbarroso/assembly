(ns revoir.cli
  (:require [revoir.core :as r]
            [clojure.tools.cli :as cli]
            [clojure.string :as str]))

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

(defn- handler [input output]
  (try
    (let [file-contents (r/read-file input)
          file-tree (r/parse file-contents)
          instructions (r/transform file-tree)]
      (r/write-file instructions output))
    (catch Exception e
      (println "deu erro: " e))))

(defn -main [& args]
  (let [{:keys [options errors]} (cli/parse-opts args cli-options)]
    (cond errors
          (exit 1 (error-msg errors))

          (:help options)
          (exit 0 "vamo ver")

          :else
          (handler (:input options)
                   (:output options)))))
