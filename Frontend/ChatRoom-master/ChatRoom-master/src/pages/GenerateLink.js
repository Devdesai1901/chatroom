import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

export default function GenerateLink() {
    const location = useLocation();
    const { role } = location.state;
    const [generatedLink, setGeneratedLink] = useState("");
    var response="";
   
    const generateLink = () => {
        axios.post("http://localhost:8080/link/generate")
        .then((response) => {
          setGeneratedLink(response.data);
        });
      };
    return (
        <>
            <section className="bg-[#5b5656] min-h-screen flex items-center justify-center">
                <div className="bg-gray-100 flex rounded-2xl shadow-lg max-w-3xl p-5 items-center">
                    <div className="md:w-screen px-16">
                        <h2 className="font-bold text-3xl text-[#5b5656] text-center">
                            Generate Link
                        </h2>
                        <div action="" className="flex flex-col gap-4">
                            <input
                                className="p-2 mt-8 rounded-xl border"
                                type="text"
                                name="link"
                                value={generatedLink}
                                readOnly
                            // onChange={(event) => {
                            //   setTopic(event.target.value);
                            // }}
                            />
                            <button className="bg-[#5b5656] rounded-xl text-white hover:scale-105 duration-300 py-2" onClick={generateLink} status={{role}}>
                                Enter
                            </button>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}
