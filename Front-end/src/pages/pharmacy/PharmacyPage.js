import React, { useEffect, useState } from "react";
import { loadLocalStorage } from "../../utils/persistLocalStorage";
import { sendGetRequest } from "../../apis/api";
import { GET_ALL_AVAILABLE_MEDICINE } from "../../utils/urls";
import { medicinePlaceholder } from "../../utils/constants";

function PharmacyPage() {
  const token = loadLocalStorage("token");

  const [medicineList, setMedicineList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchMedicineList();
  }, []);

  const fetchMedicineList = () => {
    setIsLoading(true);
    setError(null);
    sendGetRequest(GET_ALL_AVAILABLE_MEDICINE)
      .then((response) => {
        console.log(response);
        setMedicineList(response.data);
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
      <section class="page-title bg-1">
        <div class="overlay"></div>
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="block text-center">
                <span class="text-white">Buy Your Meds</span>
                <h1 class="text-capitalize mb-5 text-lg">Pharmacy</h1>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section class="section doctors">
        <div class="container">
          <div class="row shuffle-wrapper portfolio-gallery">
            {medicineList.map((medicine) => {
              return (
                <div class="col-lg-3 col-sm-6 col-md-6 shuffle-item">
                  <div class="position-relative doctor-inner-box">
                    <div class="doctor-profile">
                      <div class="doctor-img text-center">
                        <img
                          src={medicinePlaceholder}
                          alt={medicine?.medicineName}
                          class="img-fluid"
                          style={{
                            height: "120px",
                          }}
                        />
                      </div>
                      <div class="content mt-3 text-center">
                        <span class="h4">{medicine?.medicineName}</span>
                        <br />
                        <span>Generic: {medicine?.generic}</span>
                        <br />
                        <span>Manufacturer: {medicine?.manufacturer}</span>
                        <br />
                        <span>Type: {medicine?.medicineType}</span>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </section>
    </div>
  );
}

export default PharmacyPage;
