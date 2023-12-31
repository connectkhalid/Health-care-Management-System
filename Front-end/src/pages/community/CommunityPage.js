import React, { useEffect, useState } from "react";
import { loadLocalStorage } from "../../utils/persistLocalStorage";
import { sendGetRequest } from "../../apis/api";
import { GET_ALL_POST } from "../../utils/urls";
import { articlePlaceholder } from "../../utils/constants";
import { formatDateTime } from "../../utils/utils";

function CommunityPage() {
  const token = loadLocalStorage("token");
  const user = loadLocalStorage("user");

  const [articles, setArticles] = useState([]);
  const [latestArticles, setLatestArticles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchArticles();
  }, []);

  const fetchArticles = () => {
    setIsLoading(true);
    setError(null);
    sendGetRequest(GET_ALL_POST, token)
      .then((response) => {
        console.log(response);
        setArticles(response.data);
        setIsLoading(false);
        setError(null);
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
      });
  };

  return (
    <div>
      <section className="page-title bg-1">
        <div className="overlay"></div>
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <div className="block text-center">
                <span className="text-white">Community Portal</span>
                <h1 className="text-capitalize mb-5 text-lg">Articles</h1>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="section blog-wrap">
        <div className="container">
          <div className="row">
            <div className="col-lg-8">
              <div className="row">
                {articles.map((article) => {
                  return (
                    <div className="col-lg-12 col-md-12 mb-5">
                      <div className="blog-item">
                        <div className="blog-thumb">
                          <a href={`/community/${article?.postId}`}>
                            <img
                              src={articlePlaceholder}
                              alt={article?.postTitle}
                              className="img-fluid rounded"
                            />
                          </a>
                        </div>

                        <div className="blog-item-content">
                          <div className="blog-item-meta mb-3 mt-4">
                            <span className="text-color-2 text-capitalize mr-3">
                              <i className="icofont-book-mark mr-2"></i>{" "}
                              {article?.category}
                            </span>

                            <span className="text-black text-capitalize mr-3">
                              <i className="icofont-calendar mr-1"></i>{" "}
                              {formatDateTime(article?.postTime)}
                            </span>
                          </div>

                          <h2 className="mt-3 mb-3">
                            <a href={`/community/${article?.postId}`}>
                              {article?.postTitle}
                            </a>
                          </h2>

                          <p className="mb-4">
                            {article?.postContent.slice(0, 200)}...
                          </p>

                          <a
                            href={`/community/${article?.postId}`}
                            className="btn btn-main btn-icon btn-round-full"
                          >
                            Read More{" "}
                            <i className="icofont-simple-right ml-2"></i>
                          </a>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>

            <div className="col-lg-4">
              {user?.role === "PATIENT" && (
                <a
                  href="/community/create"
                  className="btn btn-main btn-round-full m-3"
                >
                  <i className="fa fa-plus"></i>
                  <span> Post a New Article</span>
                </a>
              )}
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default CommunityPage;
