import React, { useEffect, useState } from "react";
import axios from "axios";
import { Navigate } from "react-router-dom";
import { API_URL } from "../config";

function LoginRegister() {
  const tokenLoginUrl = API_URL + "loginWithToken";
  useEffect(() => {
    loginTest();
  });
  function redirect(url) {
    window.location.href = url;
  }


  const loginTest = async () => {
    const config = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    };

    const navigate = (path) => {
      return <Navigate to={path} replace={true} />;
    };
    console.log("test", localStorage.getItem("token"));
    console.log(config);

    if (localStorage.getItem("token") === null) {
      return navigate("/reglog");
    } else {
      try {
        const response = await axios.get(tokenLoginUrl, config);

        if (response.data.success === true) {
          console.log("noluyor aq");
          localStorage.setItem(
            "token",
            JSON.stringify(response.data.updatedToken).slice(1, -1)
          );
          redirect("/home");
        }
      } catch (error) {
        console.error("An error occurred:", error);
      }
    }
  };

  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  function redirectHome() {
    window.location.href = "/home";
  }

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endpoint = isLogin ? "login" : "register";

    const requestData = {
      username: formData.username,
      password: formData.password,
    };

    try {
      const response = await axios.post(`${API_URL}${endpoint}`, requestData);
      console.log("Response:", response.data);
      localStorage.setItem("token", response.data.token);
      if (response.data.token !== null) {
        console.log("noluyo aq");
        redirectHome();
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-gray-500 to-gray-600">
      <div className="bg-white p-8 rounded shadow-md w-96">
        <div className="text-center mb-6">
          <h2 className="text-2xl text-red-600">
            {isLogin ? "Login" : "Register"}
          </h2>
        </div>
        <form
          onSubmit={handleSubmit}
          className={`${isLogin ? "login" : "register"}-form`}
        >
          <div className="mb-4">
            <label htmlFor="username" className="block mb-1 text-gray-600">
              Username
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleFormChange}
              className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-red-400"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block mb-1 text-gray-600">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleFormChange}
              className="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-red-400"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-red-500 text-white py-2 rounded hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-400"
          >
            {isLogin ? "Login" : "Register"}
          </button>
        </form>
        <div className="mt-4 text-center text-gray-600">
          {isLogin ? "Don't have an account?" : "Already have an account?"}
          <br />
          <button
            className="mt-4 text-red-500 hover:underline focus:outline-none"
            onClick={() => setIsLogin(!isLogin)}
          >
            {isLogin ? "Register instead" : "Login instead"}
          </button>
        </div>
      </div>
    </div>
  );
}

export default LoginRegister;
