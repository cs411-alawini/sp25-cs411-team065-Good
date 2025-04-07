import { useSearchParams } from 'react-router-dom';

function Search() {
    const [searchParams] = useSearchParams();
    const query = searchParams.get('query');

    return <h1>Search Result: {query}</h1>;
}

export default Search;