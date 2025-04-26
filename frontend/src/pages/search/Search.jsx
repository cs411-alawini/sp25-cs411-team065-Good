import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import AttractionList from "../../components/items-list/attractionList"; // 引入通用组件
import TopHeader from '../header/header.jsx';
// import result from "./result.json";
import "./search.css"; // 引入样式

function Search() {
  const [searchParams] = useSearchParams();
  const state = searchParams.get("state");

  const [attractions, setAttractions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchAttractions() {
      setLoading(true);
      try {
        const res = await fetch(`http://35.226.211.97:8080/api/attractions/state?state=${state}`);
        const result = await res.json();
        setAttractions(result.code === "200" ? result.data : []);
      } catch (err) {
        console.error("Fetch error:", err);
        setAttractions([]);
      } finally {
        setLoading(false);
      }
    }

    if (state) fetchAttractions();
  }, [state]);

  // useEffect(() => {
  //   setAttractions(result.data); // 这里就能用了
  //   setLoading(false); // ✅ 这里补上
  // }, []);

  return (
    <div>
      <TopHeader/>
      <div className="search-page">
        <h1 className="search-title">Search Results for "{state}"</h1>
        {loading ? <p>Loading...</p> : <AttractionList data={attractions} />}
      </div>
    </div>
  );
}

export default Search;
