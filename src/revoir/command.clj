(ns revoir.command
  (:require [revoir.core :as r]
            [babashka.cli :as cli]
            [clojure.string :as str]))

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

(defn- parse-error [{:keys [spec type cause msg option] :as data}]
  (if (= :org.babashka/cli type)
    (case cause
      :require
      (let [field-spec (select-keys spec [option])
            field (first (keys field-spec))
            field-data (first (vals field-spec))]
        (println (format "Missing required argument:\n%s"
                         (str "-" (name field) " (" (:desc field-data) ")")))
        (println msg)))
    (throw (ex-info msg data)))
  (System/exit 1))

(def cli-options-v2
  {:input {:ref "<input>"
           :desc "Input file"
           :coerce :string
           :require true}
   :output {:ref "<output>"
            :desc "Output file"
            :coerce :string
            :require true}})

(defn -main [& args]
  (let [{:keys [input output errors] :as x}
        (cli/parse-opts args {:spec cli-options-v2
                              :error-fn parse-error})]
    (cond errors
          (exit 1 (error-msg errors))

          :else
          (handler input
                   output))))
