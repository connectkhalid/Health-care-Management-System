import React, { useEffect, useState } from "react";
import { sendGetRequest } from "../../apis/api";
import { userPlaceholder } from "../../utils/constants";
import { GET_ALL_AVAILABLE_DOCTOR_URL } from "../../utils/urls";
import { loadLocalStorage } from "../../utils/persistLocalStorage";

function DoctorListPage() {
  const token = loadLocalStorage("token");
  var user = loadLocalStorage("user");

  const [doctorList, setDoctorList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    getDoctorList();
  }, []);

  const getDoctorList = () => {
    sendGetRequest(GET_ALL_AVAILABLE_DOCTOR_URL, token)
      .then((response) => {
        console.log(response);
        setDoctorList(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
      });
  };

  return (
    <>
      <section class="section doctors">
        <div class="container">
          {isLoading ? (
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-lg-6">
                  <div className="section-title text-center">
                    <h2 className="text-md mb-2">Loading...</h2>
                    <div className="divider mx-auto my-4"></div>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <>
              <div class="page-title bg-1 mb-3">
                <div class="overlay"></div>
                <div class="container">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="block text-center">
                        <h1 class="text-capitalize mb-5 text-lg">
                          Specalized doctors
                        </h1>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row shuffle-wrapper portfolio-gallery">
                {doctorList.map((doctor, index) => {
                  return (
                    <div
                      class="col-lg-3 col-sm-6 col-md-6 mb-4 shuffle-item"
                      key={index}
                    >
                      <div class="position-relative doctor-inner-box">
                        <div class="doctor-profile">
                          <div class="doctor-img">
                            <img
                              src={userPlaceholder}
                              alt={doctor?.name}
                              class="img-fluid w-100"
                            />
                          </div>
                        </div>
                        <div class="content mt-3 text-center">
                          <h4 class="mb-0">
                            <a href={`/create-appointment/${doctor.doctor_id}`}>{doctor?.name}</a>
                          </h4>
                          <p>{doctor?.department}</p>
                        </div>
                        <div className="text-center">
                          <a
                            href={`/create-appointment/${doctor.doctor_id}`}
                            className="btn btn-main"
                          >
                            Book Appointment
                          </a>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </>
          )}
        </div>
      </section>
    </>
  );
}

export default DoctorListPage;
