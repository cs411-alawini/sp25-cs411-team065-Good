import React from "react";
import { Button, Checkbox, Form, Input, message } from "antd";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // 引入样式

const Login = () => {
  const navigate = useNavigate();
  //const location = useLocation(); // 获取当前的 location，用于读取跳转前页面

  const onFinish = async (values) => {
    //console.log("Success:", values);

    try {
      // 假设这是后端登录接口
      const res = await fetch("http://35.226.211.97:8080/api/user/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(values),
      });

      const result = await res.json();

      if (result.code === "200") {
        const sessionId = result.data.token;
        const userId = result.data.user.id;  
        localStorage.setItem("sessionId", sessionId);
        localStorage.setItem("userId", userId); // 存储 userId
        //console.log("sessionId from response:", sessionId);
        message.success("Login successful!");

        // 👇 登录前想去的页面（如 /search?state=Illinois）
        navigate("/homePage");
      } else {
        message.error(result.msg || "Login failed");
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
        name="login"
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        style={{ maxWidth: 600 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
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

        <Form.Item name="remember" valuePropName="checked" label={null}>
          <Checkbox>Remember me(keep log in within 7 days)</Checkbox>
        </Form.Item>

        <Form.Item label={null}>
          <Button type="primary" htmlType="submit">
            Log in
          </Button>
        </Form.Item>

        
          <p className="jump-to-signup">
            Don't have an account?{" "}
            <a onClick={() => navigate("/signup")}>Sign up</a>
          </p>
        
      </Form>
    </div>
  );
};

export default Login;
