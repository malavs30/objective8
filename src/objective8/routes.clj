(ns objective8.routes
  (:require [bidi.bidi :as bidi]
            [bidi.ring :refer [->Resources]]))

(def routes
  [
   "/"  ;; FRONT-END
        {""                 :fe/index
        "sign-in"           :fe/sign-in
        "sign-out"          :fe/sign-out
        "project-status"    :fe/project-status
        "learn-more"        :fe/learn-more
        "static/"           (->Resources {:prefix "public/"})
        "objectives"        {:get :fe/objective-list
                             :post :fe/create-objective-form-post
                             "/create" {:get :fe/create-objective-form} 
                             ["/" [#"\d+" :id]] {:get :fe/objective
                                        "/writer-invitations" {:post :fe/invitation-form-post
                                                               ["/" [#"\d+" :i-id]] {:get :fe/accept-or-decline-invitation
                                                                                     "/accept" {:post :fe/accept-invitation}
                                                                                     "/decline" {:post :fe/decline-invitation}
                                                                                     }}
                                        "/candidate-writers" {:get :fe/candidate-list}
                                        "/questions" {:post :fe/add-question-form-post
                                                      :get :fe/question-list
                                                      ["/" [#"\d+" :q-id]] {:get :fe/question
                                                                            "/answers" {:post :fe/add-answer-form-post}}}
                                                 "/drafts" {["/" [#"\d+|current" :d-id]] {:get :fe/draft}}
                                        "/edit-draft" {:get :fe/edit-draft-get
                                                       :post :fe/edit-draft-post}}}
        "comments"          {:post :fe/create-comment-form-post}
        "invitations"       {["/" :uuid] {:get :fe/writer-invitation}}

        ;; API
        "api/v1"            {"/users" {:post :api/post-user-profile
                                       :get :api/get-user-by-query
                                       ["/" [#"\d+" :id]] :api/get-user}

                             "/objectives" {:get :api/get-objectives
                                            :post :api/post-objective
                                            ["/" [#"\d+" :id]] {:get :api/get-objective
                                                       "/comments" :api/get-comments-for-objective
                                                       "/questions" {:post :api/post-question
                                                                     :get :api/get-questions-for-objective
                                                                     ["/" [#"\d+" :q-id]] {:get :api/get-question
                                                                                  "/answers" {:get :api/get-answers-for-question
                                                                                              :post :api/post-answer}}}
                                                       "/candidate-writers" {:get :api/get-candidates-for-objective
                                                                             :post :api/post-candidate-writer}
                                                       "/writer-invitations" {:post :api/post-invitation
                                                                              ["/" [#"\d+" :i-id]] {:put :api/put-invitation-declination}}
                                                       "/drafts" {:post :api/post-draft
                                                                  ["/" [#"\d+|current" :d-id]] {:get :api/get-draft}}}}

                             "/comments"   {:post :api/post-comment}
                             "/up-down-votes" {:post :api/post-up-down-vote}
                             "/invitations" {:get :api/get-invitation}}

         ;;DEV-API
        "dev/api/v1"     {["/objectives/" :id "/start-drafting"] {:post :dev/post-start-drafting}}}])