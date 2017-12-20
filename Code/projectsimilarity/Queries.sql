select pull_requests.id as pullRequestId, created_at, action 
from pull_request_history, pull_requests 
where pull_requests.id=pull_request_history.pull_request_id  
limit 10;

select pull_requests.id as pullRequestId, pull_request_history.created_at as pullRequestCreatedAt, `action`, comment_id, body, commit_id
from pull_request_history, pull_requests, pull_request_comments
where pull_request_history.pull_request_id=pull_requests.id 
and pull_request_comments.pull_request_id=pull_requests.id
limit 10;

select pull_requests.id as pullRequestId, pull_request_history.created_at as pullRequestCreatedAt, `action`, comment_id, body, commit_id
from pull_request_history, pull_requests, pull_request_comments
where pull_request_history.pull_request_id=pull_requests.id 
and pull_request_comments.pull_request_id=pull_requests.id
limit 10;


select commit_comments.comment_id, commit_comments.created_at as commitCreationTime, commit_comments.body, 
pull_request_comments.commit_id, SUBSTRING(pull_request_comments.body FROM 0 FOR 10) 
from commit_comments,pull_request_comments, commits, projects 
where commit_comments.commit_id = pull_request_comments.commit_id limit 10;