import React, { useState } from "react";
import { loadLocalStorage } from "../../utils/persistLocalStorage";
import { sendPostRequest } from "../../apis/api";
import { CREATE_POST_BY_PATIENT } from "../../utils/urls";

function CommunityPostCreatePage() {
  const token = loadLocalStorage("token");
  var user = loadLocalStorage("user");

  const [postData, setPostData] = useState({
    patientId: user?.id,
    postTitle: "",
    postContent: "",
    category: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setPostData({
      ...postData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(postData);

    sendPostRequest(CREATE_POST_BY_PATIENT, postData, token)
      .then((response) => {
        console.log(response);
        setIsLoading(false);
        setError(null);
        alert("Post created successfully!");
        window.location.href = "/community";
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
        // setError(error.response.data.message);
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
                <span class="text-white">Community Portal</span>
                <h1 class="text-capitalize mb-5 text-lg">Create a New Post</h1>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section class="container">
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12">
            <div class="row">
              <div class="col-lg-12 col-md-12 col-sm-12">
                <form class="p-3" onSubmit={(e) => handleSubmit(e)}>
                  <h3 class="text-md text-center mb-3">Create a New Post</h3>

                  <div class="row">
                    <div class="col-lg-12">
                      <div class="form-group">
                        <label>Post Title</label>
                        <input
                          type="text"
                          name="postTitle"
                          placeholder="Post Title"
                          value={postData.postTitle}
                          onChange={handleChange}
                          className="form-control"
                          required
                        />
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-lg-12">
                      <div class="form-group">
                        <label>Post Category</label>
                        {/* add 10 medical categories */}
                        <select
                          name="category"
                          value={postData.category}
                          onChange={handleChange}
                          className="form-control"
                          required
                        >
                          <option value="">Select a Category</option>
                          <option value="GENERAL">General</option>
                          <option value="CARDIOLOGY">Cardiology</option>
                          <option value="DERMATOLOGY">Dermatology</option>
                          <option value="ENDOCRINOLOGY">Endocrinology</option>
                          <option value="GASTROENTEROLOGY">
                            Gastroenterology
                          </option>
                          <option value="NEUROLOGY">Neurology</option>
                          <option value="ONCOLOGY">Oncology</option>
                          <option value="ORTHOPEDICS">Orthopedics</option>
                          <option value="PEDIATRICS">Pediatrics</option>
                          <option value="PSYCHIATRY">Psychiatry</option>
                        </select>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-lg-12">
                      <div class="form-group">
                        <label>Post Content</label>
                        <textarea
                          name="postContent"
                          placeholder="Post Content"
                          rows={10}
                          value={postData.postContent}
                          onChange={handleChange}
                          className="form-control"
                          required
                        />
                      </div>
                    </div>
                  </div>

                  {error && (
                    <div className="row">
                      <div className="col-lg-12">
                        <div className="form-group">
                          <div className="alert alert-danger" role="alert">
                            {error}
                          </div>
                        </div>
                      </div>
                    </div>
                  )}

                  {isLoading ? (
                    <div className="row">
                      <div className="col-lg-12">
                        <div className="form-group">
                          <div className="alert alert-info" role="alert">
                            Loading...
                          </div>
                        </div>
                      </div>
                    </div>
                  ) : null}

                  <div class="text-center">
                    <button class="btn btn-main btn-round-full" type="submit">
                      Create a New Post
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default CommunityPostCreatePage;
