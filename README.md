# microblog
Blog that supports REST API:

POST: /posts
Example request body: {"userId" : "userid1", "body": "post body"}
Returns: The entity that was created in the DB

GET: /posts?postId={postId} || /posts?userId={userId} || /posts
Returns: List of post entities that are saved in the DB

PUT: /posts/{postId}
Example request body: {"body" : "new post body to update"}
Returns: The updated entity

DELETE: /posts/{postId}
Returns: No content if deleted succesfully

POST: /posts/like/{postId}
Returns: The liked entity with updated likes
