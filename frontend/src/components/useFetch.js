import { useEffect, useState } from "react";

const useFetch = (url) => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        //const abortConstant = new AbortController();
        fetch(url)
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Could not fetch the data from the resource`);
                }
                return res.json();
            }).then(data => {
                setData(data);
                setLoading(false);
                setError(null);
            }).catch(err => {
                //if (!err.name === "AbortError") {
                    setLoading(false);
                    setError(err.message);
                //}
            });
            //return () => abortConstant.abort();
    }, [url])
    return { data, error, loading }
}

export default useFetch;