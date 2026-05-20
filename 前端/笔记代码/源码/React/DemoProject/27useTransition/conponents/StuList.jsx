

const STU_DATA=[];

for(let i=1;i<=100;i++){
    STU_DATA.push(
        <li key={i}>学生{i}</li>
    );
}



const StuList=(props)=>{

    console.log(props.keyword);

    const time=Date.now();

    while(Date.now()-time<=3000){

    }

    return (
        <ul>
            {
                STU_DATA.filter(
                    (item)=>{
                        return item.key.indexOf(props.keyword)>=0?true:false;
                    }
                )           
            }
        </ul>
    );
};

export default StuList;