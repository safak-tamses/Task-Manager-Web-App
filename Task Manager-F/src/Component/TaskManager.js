import React, { useState, useEffect } from "react";
import axios from "axios";
import Alert from "./Alert";
import { API_URL } from "../config";
const url = API_URL + "user/addTask";
const url2 = API_URL + "user/showTasks";
const url3 = API_URL + "user/deleteTask";
function quit() {
  localStorage.removeItem("token");
  window.location.href = "/";
}
function tokenCheck() {
  if (localStorage.getItem("token") === null) {
    window.location.href = "/";
  }
}

const getRandomColor = () => {
  const colors = [
    "bg-red-300",
    "bg-yellow-300",
    "bg-green-300",
    "bg-blue-300",
    "bg-purple-300",
    "bg-pink-300",
  ];
  return colors[Math.floor(Math.random() * colors.length)];
};

function TaskManager() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [subject, setSubject] = useState("");
  const [text, setText] = useState("");
  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    tokenCheck();
    async function fetchTasks() {
      try {
        const config = {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        };

        const response = await axios.get(url2, config);

        // Renkli taskları oluştur
        const colorfulTasks = response.data.map((task) => ({
          ...task,
          color: getRandomColor(),
        }));

        setTasks(colorfulTasks); // Renkli taskları state'e kaydet
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    }

    fetchTasks();
  }, []);

  const handleAddTask = async () => {
    if (title.trim() !== "" && subject.trim() !== "" && text.trim() !== "") {
      const task = { title, subject, text, color: getRandomColor() };
      try {
        const config = {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        };
        const data = {
          title: title,
          subject: subject,
          text: text,
        };

        const response = await axios.post(url, data, config);
        console.log(response.data);
        localStorage.setItem("token", response.data.updatedToken);
      } catch (error) {
        console.error("Error adding task:", error);
      }
      setTasks([...tasks, task]);
      setTitle("");
      setSubject("");
      setText("");
      setShowAlert(false);
    } else {
      setShowAlert(true);
    }
  };

  const handleDeleteTask = async (index, id) => {
    const newTasks = tasks.filter((_, i) => i !== index);
    setTasks(newTasks);
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      };

      const urlWithParams = `${url3}?id=${id}`;
      const response = await axios.delete(urlWithParams, config); // params objesini kaldırdık
      console.log(response.data);
      localStorage.setItem("token", response.data.updatedToken);
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  return (
    <div className="container mx-auto p-4 bg-gradient-to-r from-sky-200 via-sky-200 to-sky-200 min-h-screen">
      <div className="flex flex-row justify-between">
        <h1 className="text-2xl font-bold mb-4">Task Manager</h1>
        <button
          className="bg-red-500 text-white px-4 py-2 rounded md:col-span-4"
          onClick={() => quit()}
        >
          Quit
        </button>
      </div>

      <div className="mb-4 grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="flex flex-col">
          <label className="mb-1 font-semibold">Title</label>
          <input
            type="text"
            className="border p-2 shadow-inner rounded"
            placeholder="Enter title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <div className="flex flex-col">
          <label className="mb-1 font-semibold">Subject</label>
          <input
            type="text"
            className="border p-2 shadow-inner rounded"
            placeholder="Enter subject"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
          />
        </div>
        <div className="flex flex-col col-span-2">
          <label className="mb-1 font-semibold">Text</label>
          <textarea
            className="border p-2 shadow-inner rounded"
            placeholder="Enter text"
            rows="4"
            value={text}
            onChange={(e) => setText(e.target.value)}
          />
        </div>
        <button
          className="bg-blue-500 text-white px-4 py-2 rounded md:col-span-4"
          onClick={handleAddTask}
        >
          Add Task
        </button>
      </div>
      {showAlert && (
        <Alert
          message="Please fill in all fields."
          onClose={() => setShowAlert(false)}
        />
      )}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        {tasks.map((task, index) => (
          <div key={index} className={`border p-4 ${task.color}`}>
            <h2 className="text-lg font-semibold">Title: {task.title}</h2>
            <p className="text-gray-600 mb-2">Subject: {task.subject}</p>
            <p>Content: {task.text}</p>
            <p>Creation Date: {task.creationDate}</p>
            <p>Task Id: {task.id}</p>
            <button
              className="text-red-500 mt-2"
              onClick={() => handleDeleteTask(index, task.id)}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TaskManager;
