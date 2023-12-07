# kameleoon
API:
  userService:
    POST: /api/v1/user 
      body: {
      "username" : "name",
      "email" : "email@g.com",
      "password" : "password"
      }
    GET: /api/v1/user/{id}
    
  voteService:
    GET: /api/v1/vote/{id}
    
  quoteService:
    POST: /api/v1/quote
      body: {
        "content" : "content",
        "userId" : 1
        }
    GET: /api/v1/quote/random
    GET: /api/v1/quote/last
    GET: /api/v1/quote/top
    GET: /api/v1/quote/flop
    PATCH:
      body: {
          "content" : "content",
          "userId" : 1
          }
    DELETE: /api/v1/quote/{id}
    PUT: /api/v1/quote/{userId}/user/{voteId}/vote
    PUT: /api/v1/quote/{userId}/user/{voteId}/un-vote

