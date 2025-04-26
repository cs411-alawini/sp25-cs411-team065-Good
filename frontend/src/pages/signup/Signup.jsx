import "./Signup.css";
import React from "react";
import { Button, Form, Input, message } from "antd";
import { useNavigate } from "react-router-dom";

const Signup = () => {
  const navigate = useNavigate();

  const onFinish = async (values) => {
    //console.log("Register values:", values);

    try {
      const res = await fetch("http://35.226.211.97:8080/api/user/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(values),
      });

      const result = await res.json();

      if (result.code === "200") {
        const sessionId = result.data.token;
        localStorage.setItem("sessionId", sessionId);
        //message.success("Register successful! Please log in.");
        navigate("/homePage");
      } else {
        message.error(result.msg || "Register failed");
      }
    } catch (err) {
      console.error(err);
      message.error("Network error");
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <div className="container">
      <Form
        className="login-form"
        name="register"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        style={{ maxWidth: 600 }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="Username"
          name="name"
          rules={[{ required: true, message: "Please input your username!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Email"
          name="email"
          rules={[{ required: true, message: "Please input your email!" }]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: "Please input your password!" }]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item label={null}>
          <Button type="primary" htmlType="submit">
            Register
          </Button>
        </Form.Item>

        <p className="jump-to-signup">
          Already have an account?{" "}
          <a onClick={() => navigate("/login")}>Log in</a>
        </p>
      </Form>
    </div>
  );
};

export default Signup;
