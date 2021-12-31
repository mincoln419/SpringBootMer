import { useState, useCallback } from "react";

export default (initialValues = null) =>{
    const [value, setValue] = useState(initialValues);
    const handler = useCallback((e) => {
        setValue(e.target.value);
    }, []);
    return [value, handler];
}