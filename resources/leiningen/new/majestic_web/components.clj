(ns {{name}}.components
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.form :refer [hidden-field text-field submit-button]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(defn layout [content]
  (html5
    [:head
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     (include-css "//unpkg.com/tachyons@4.7.0/css/tachyons.min.css")]
    [:body
     content
     (include-js "//cdnjs.cloudflare.com/ajax/libs/turbolinks/5.0.0/turbolinks.min.js")]))

(defn center [& content]
  [:div {:class "vh-100 flex flex-column justify-center items-center"}
   content])

(defn link
  ([url content attrs]
   [:a (merge {:href url} attrs) content])
  ([url content]
   (link url content nil)))

(defn info [content]
  [:div {:class "pa3 w-100 mb2 bg-lightest-blue navy tc"}
   [:span {:class "lh-title"} content]])

(defn render-optional [optional]
  (if (true? optional)
    [:span {:class "normal black-80"}
     "(optional)"]
    nil))

(defn label
  ([{:keys [for optional]} & content]
   [:label {:for for :class "f6 b db mb2"}
    content
    (render-optional optional)])
  ([content]
   [:label {:class "f6 b db mb2"}
    content]))

(defn input [{:keys [type name value placeholder class]}]
  [:input {:name name
           :class (str "input-reset ba b--black-20 pa2 mb2 db " (or class "w5"))
           :type type
           :placeholder placeholder
           :value (or value "")}])

(defn submit [value]
  [:input {:class "b bn ph3 pv2 input-reset bg-blue white grow pointer f6 dib w-100"
           :type "submit"
           :value value}])

(defn csrf-field []
  (hidden-field "__anti-forgery-token" *anti-forgery-token*))

(defn form [{:keys [method action]} & content]
  [:form {:method (or method "post") :action action :class "pa4 black-80"}
   (csrf-field)
   content])

(defn error-page [error]
  (let [message (.getMessage error)]
    (layout
      (center
        message))))

(defn forbidden-page []
  (layout
    (center
      "I can't let you do that")))

(defn not-found-page []
  (layout
    (center
      "Sorry, couldn't find what you were looking for")))
