import React ,{useState}from "react";
import Border from "../Components/Border";
import "../Style/User_register.css";
import { Link, useLocation } from "react-router-dom";
import axios from "axios";

export default function UserRegister() {
  const location = useLocation();
  const { role } = location.state;
  const [myrole,setMyrole] = useState(role)
  const handleSubmit = async (e) => {
    e.preventDefault();

    const name = e.target.name.value;
    const email_id = e.target.email_id.value;
    const password = e.target.password.value;
    const branch = e.target.branch.value;
    const sem = e.target.sem.value;
    const collage_id = e.target.collage_id.value;
    const srole = e.target.role.value;
    setMyrole(e.target.role.value);

    const obj = {
      name: name,
      branch: branch,
      sem: sem,
      collage_id: collage_id,
      email_id: email_id,
      password: password,
      role: srole,
    };

    try {
      const res = await axios.post("http://localhost:8080/registration/registeruser", obj);
      console.log(obj);
      // console.log(res.data);
      alert("Registration successful!! You may now login");
    } catch (err) {
      alert(err);
    }
  };
  return (
    <>
      <section className="bg-[#413D3D] min-h-screen flex items-center justify-center">
        <div className="bg-gray-100 flex rounded-2xl shadow-lg max-w-3xl p-5 items-center">
          <div className="md:w-screen px-16">
            <h2 className="font-bold text-2xl text-[#413D3D]">Registration</h2>
            <Border />
            <form
              action=""
              className="flex flex-col gap-4"
              onSubmit={handleSubmit}
            >
              <input
                className="p-2 rounded-xl border"
                type="text"
                name="name"
                placeholder="Enter Full Name"
              />
              <input
                className="p-2 rounded-xl border"
                type="text"
                name="branch"
                placeholder="Enter branch"
              />
              <input
                className="p-2 rounded-xl border"
                type="text"
                name="sem"
                placeholder="Enter Semester"
              />
              <input
                className="p-2 rounded-xl border"
                type="text"
                name="collage_id"
                placeholder="Enter College ID"
              />
              <input
                className="p-2 rounded-xl border"
                type="email"
                name="email_id"
                placeholder="Enter Your Email"
              />
              <input
                className="p-2 rounded-xl border"
                type="password"
                name="password"
                placeholder="Enter Password"
              />
              <select
                className="form-select py-2"
                name="role"
                aria-label="Default select example"
              >
                <option selected>Select Role</option>
                <option value="ADMIN">Admin</option>
                <option value="HOST">Host</option>
                <option value="USER">User</option>
              </select>
              <button className="bg-[#413D3D] rounded-xl text-white hover:scale-105 duration-300 py-2">
                  Submit
              </button>
            </form>
            <div className="mt-3 text-xs flex justify-between items-center">
              <p>If you are already member....</p>
              <Link exact to="/login" state={{myrole}} >
                <button className="py-2 px-5 bg-white hover:scale-110 duration-300 border rounded-xl justify-center items-center">
                  Login
                </button>
              </Link>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}