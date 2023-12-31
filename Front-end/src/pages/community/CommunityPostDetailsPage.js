import React, { useEffect, useState } from "react";
import { loadLocalStorage } from "../../utils/persistLocalStorage";
import { articlePlaceholder, userPlaceholder } from "../../utils/constants";
import { sendGetRequest, sendPostRequest } from "../../apis/api";
import {
  CREATE_COMMENT,
  GET_ALL_COMMENT_BY_POST_ID,
  GET_POST_BY_POST_ID,
} from "../../utils/urls";
import { formatDateTime } from "../../utils/utils";

function CommunityPostDetailsPage() {
  const postId = window.location.pathname.split("/")[2];
  const token = loadLocalStorage("token");

  const [post, setPost] = useState({});
  const [comments, setComments] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const [newCommentData, setNewCommentData] = useState({
    postId: postId,
    commentContent: "",
  });

  useEffect(() => {
    fetchPostDetails();
    fetchComments();
  }, []);

  const fetchPostDetails = () => {
    setIsLoading(true);
    setError(null);
    sendGetRequest(GET_POST_BY_POST_ID + "/" + postId)
      .then((response) => {
        console.log(response);
        setPost(response.data);
        setIsLoading(false);
        setError(null);
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
      });
  };

  const fetchComments = () => {
    setIsLoading(true);
    setError(null);
    sendGetRequest(GET_ALL_COMMENT_BY_POST_ID + "/" + postId, token)
      .then((response) => {
        console.log(response);
        setComments(response.data);
        setIsLoading(false);
        setError(null);
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
      });
  };

  const handleCreateComment = (e) => {
    e.preventDefault();
    console.log(newCommentData);

    sendPostRequest(CREATE_COMMENT, newCommentData, token)
      .then((response) => {
        console.log(response);
        setIsLoading(false);
        setError(null);
        alert("Comment created successfully!");
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
        setError(error.response.data.message);
      });
  };

  return (
    <div>
      <section class="page-title bg-1">
        <div class="overlay"></div>
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="block text-center">
                <span class="text-white">Community Post Details</span>
                <h1 class="text-capitalize mb-5 text-lg">{post?.postTitle}</h1>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section class="section blog-wrap">
        <div class="container">
          <div class="row">
            <div class="col-lg-8">
              <div class="row">
                <div class="col-lg-12 mb-5">
                  <div class="single-blog-item">
                    <img
                      src={articlePlaceholder}
                      alt={post.title}
                      class="img-fluid rounded"
                    />

                    <div class="blog-item-content mt-5">
                      <div class="blog-item-meta mb-3">
                        <span class="text-black text-capitalize mr-3">
                          <i class="icofont-calendar mr-2"></i>{" "}
                          {formatDateTime(post?.postTime)}
                        </span>

                        <span class="text-black text-capitalize mr-3">
                          <i class="fa-solid fa-comment"></i> {comments?.length}
                        </span>
                      </div>

                      <h2 class="mb-4 text-md">{post.title}</h2>

                      <p class="lead mb-4">{post?.postContent}</p>
                      <hr />

                      <div class="row">
                        <div class="col-md-12">
                          <form onSubmit={(e) => handleCreateComment(e)}>
                            <div class="form-group">
                              <label for="comment">Write a comment</label>
                              <textarea
                                name="commentContent"
                                id="comment"
                                class="form-control"
                                value={newCommentData.commentContent}
                                onChange={(e) =>
                                  setNewCommentData({
                                    ...newCommentData,
                                    commentContent: e.target.value,
                                  })
                                }
                                required
                                rows="3"
                              ></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">
                              Submit
                            </button>
                          </form>

                          <hr />
                          <h3>Comments</h3>
                          {comments.map((comment) => {
                            return (
                              <div class="row mb-2">
                                <div class="col-md-12">
                                  <div class="card">
                                    <div class="card-body">
                                      <div class="row">
                                        <div class="col-md-2">
                                          <img
                                            src={userPlaceholder}
                                            alt={comment?.name}
                                            class="img-fluid w-100"
                                          />
                                        </div>
                                        <div class="col-md-10">
                                          <p>
                                            <span class="float-left text-primary">
                                              {comment?.name}
                                            </span>
                                          </p>
                                          <div class="clearfix"></div>
                                          <p>{comment?.commentContent}</p>
                                          <small class="text-secondary">
                                            Posted on:{" "}
                                            {formatDateTime(
                                              comment?.commentTime
                                            )}
                                          </small>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            );
                          })}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-lg-4">
              <div class="sidebar-wrap pl-lg-4 mt-5 mt-lg-0">
                {/* {% if my_article %}
								<div class="row m-3">
									<a class="btn btn-primary btn-round-full" href="{% url 'community-post-update' post.slug %}">
										<i class="far fa-edit"></i> Update Post
									</a>
								</div>
								<div class="row m-3">
									<a class="btn btn-danger btn-round-full" href="{% url 'community-post-delete' post.slug %}">
										<i class="fa fa-trash"></i> Delete Post
									</a>
								</div>
								{% endif %} */}
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default CommunityPostDetailsPage;
