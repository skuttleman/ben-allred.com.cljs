(ns com.ben-allred.components.bio)

(defn bio [{bios :bios}]
    [:div
     [:div.above-the-fold
      [:h1 "Bio"]]
     [:div.below-the-fold
      (for [{:keys [header paragraphs]} bios]
          ^{:key header}
          [:div
           [:h2 header]
           (for [paragraph paragraphs]
               ^{:key (take 10 paragraph)}
               [:p paragraph])])]])
