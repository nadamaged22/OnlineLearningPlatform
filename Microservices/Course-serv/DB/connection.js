import mongoose from "mongoose";

const ConnectDB = async()=>{

    return await mongoose.connect(`mongodb+srv://nadamaged:nnn123nnn@atlascluster.jgnsmsu.mongodb.net/test2?retryWrites=true&w=majority&appName=AtlasCluster`)
    .then(res=>{
        console.log(`DB Connected Successfully....`);
    }).catch(err=>
        {
            console.log(`Failed Connection: ${err}`);
        }

    );
}

export default ConnectDB