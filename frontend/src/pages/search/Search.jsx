import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom"; 
import Card from "../../components/search-card/search-card"; 
import "./Search.css";

function Search() {
  const [searchParams] = useSearchParams(); // 读取 URL 参数
  const state = searchParams.get("state"); // 获取 ?state=xxx 的值

  const [attractions, setAttractions] = useState([]); // 存放景点列表
  const [loading, setLoading] = useState(true); // 控制加载状态

  // 当 state 改变时，重新请求数据
  useEffect(() => {
    async function fetchAttractions() {
      setLoading(true); // 开始加载，展示 loading 状态

      try {
        // 向后端发请求，获取该 state 对应的景点数据
        const response = await fetch(`http://localhost:8080/api/attractions/state?state=${state}`);
        const result = await response.json();

        if (result.code === 200) {
          // 成功返回景点数组，存入 state 中
          setAttractions(result.data);
        } else {
          // 接口返回失败信息
          console.error("请求失败:", result.msg);
          setAttractions([]);
        }
      } catch (error) {
        // 请求出错（如后端没开）
        console.error("请求出错:", error);
        setAttractions([]);
      } finally { 
        // 无论成功或失败都要结束加载状态
        setLoading(false);
      }
    }

    if (state) {
      fetchAttractions(); // 有参数再执行请求
    }
  }, [state]); // 依赖于 state，只在 state 变化时触发

  return (
    <div className="search-page">
      <h1 className="search-title">Search Results for "{state}"</h1>

      {loading ? (
        // 加载中显示文字
        <p>Loading attractions...</p>
      ) : attractions.length === 0 ? (
        // 没有结果时显示提示
        <p>No attractions found.</p>
      ) : (
        // 正常展示卡片列表
        <div className="card-list">
          {attractions.map((item) => (
            <Card key={item.itemId} item={item} /> // 每个景点传给 Card 组件渲染
          ))}
        </div>
      )}
    </div>
  );
}

export default Search;
