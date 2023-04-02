import React, { useState } from "react";
import Border from "../Components/Border";
import "../Style/User_register.css";
import { Link } from "react-router-dom";
import axios from "axios";
import { useNavigate, useLocation, useHistory } from "react-router-dom";

export default function UserLogin() {
  const location = useLocation();
  const { role } = location.state;
  const [status, setStatus] = useState(null);
  const [token, setToken] = useState(null);
  const navigate = useNavigate();

  
  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const email = e.target.email.value;
    const password = e.target.password.value;
  
    const credentials = {
      email_id: email,
      password: password,
    };
  
    try {
      const response = await axios.post("http://localhost:8080/login/authenticate", credentials);
      const token = response.data.token; // assuming the token is returned in the response
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`; // set the default Authorization header for all future requests
      localStorage.setItem("Token",token);
      console.log(token);
      alert("You have successfully logged in");
      console.log(response.data);
      const data = { email: email, password: password };
      navigate("/otp", { state: { name: e.name, role: role ,data:data } });
      setStatus(response.status);
    } catch (err) {
      alert(err);
    }  
    

  };
  

  return (
    <>
      <section className="bg-[#5b5656] min-h-screen flex items-center justify-center">
        <div className="bg-gray-100 flex rounded-2xl shadow-lg max-w-3xl p-5 items-center">
          <div className="md:w-screen px-16">
            <h2 className="font-bold text-2xl text-[#5b5656]">
              {role && role} Login
            </h2>
            <Border />
            <form
              action=""
              className="flex flex-col gap-4"
              onSubmit={handleSubmit}
            >
              <input
                className="p-2 mt-8 rounded-xl border"
                type="email"
                name="email"
                placeholder="Enter Your Email"
                state = {{ email_id: ""}}
              />
              <input
                className="p-2 rounded-xl border"
                type="password"
                name="password"
                placeholder="Enter Password"
              />
              <button className="bg-[#5b5656] rounded-xl text-white hover:scale-105 duration-300 py-2">
                Login
              </button>
            </form>
            <div className="mt-3 text-xs flex justify-between items-center">
              <p>If you are not a member....</p>
              <Link to="/signup" state={{ role }}>
                <button className="py-2 px-5 bg-white hover:scale-110 duration-300 border rounded-xl">
                  Register
                </button>
              </Link>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}
