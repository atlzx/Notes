import { useHistory, useLocation, useParams, useRouteMatch } from "react-router-dom/cjs/react-router-dom";

const E=(props)=>{

    console.log(props);

    const match=useRouteMatch();
    const location=useLocation();
    const history=useHistory();
    const params=useParams();

    console.log(match,location,history,params);

    return (
        <div>
            这是组件E
        </div>
    );
};

export default E;