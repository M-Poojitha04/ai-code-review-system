import React, { useState } from "react";
import axios from "axios";
import Editor from "@monaco-editor/react";

function App() {

  const [file, setFile] = useState(null);
  const [response, setResponse] = useState(null);
  const [codeContent, setCodeContent] = useState("");
  const [loading, setLoading] = useState(false);

  const handleFileChange = (event) => {

    const selectedFile = event.target.files[0];

    setFile(selectedFile);

    if (selectedFile) {

      const reader = new FileReader();

      reader.onload = (e) => {

        setCodeContent(e.target.result);

      };

      reader.readAsText(selectedFile);
    }
  };

  const uploadFile = async () => {

    if (!file) {
      alert("Please select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {

      setLoading(true);

      const res = await axios.post(
          "http://localhost:8080/code/upload",
          formData
      );

      setResponse(res.data);

    } catch (error) {

      console.error(error);
      alert("Error uploading file");

    } finally {

      setLoading(false);

    }
  };

  return (

      <div className="min-h-screen bg-gradient-to-br from-gray-950 via-gray-900 to-black text-white flex flex-col items-center p-10">

        <h1 className="text-5xl font-bold mb-3">
          AI Code Review System
        </h1>

        <p className="text-gray-400 mb-10 text-lg">
          Upload your code and get intelligent review feedback
        </p>

        <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-2xl shadow-2xl p-10 w-full max-w-2xl">

          <div className="border-2 border-dashed border-gray-500 rounded-xl p-10 text-center hover:border-blue-400 transition">

            <input
                type="file"
                onChange={handleFileChange}
                className="mb-4"
            />

            <p className="text-gray-400">
              Upload .java, .py or .js files
            </p>

          </div>
          {codeContent && (

              <div className="mt-8">

                <h2 className="text-xl font-semibold mb-4 text-gray-300">
                  Code Preview
                </h2>

                <div className="rounded-xl overflow-hidden border border-gray-700">

                  <Editor
                      height="400px"
                      defaultLanguage="java"
                      theme="vs-dark"
                      value={codeContent}
                      options={{
                        readOnly: true,
                        minimap: { enabled: false },
                        fontSize: 14,
                      }}
                  />

                </div>

              </div>
          )}
          <button
              onClick={uploadFile}
              className="mt-6 w-full bg-blue-600 hover:bg-blue-700 transition py-3 rounded-xl text-lg font-semibold"
          >
            Review Code
          </button>

          {loading && (

              <div className="mt-6 text-center">

                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-white mx-auto"></div>

                <p className="mt-4 text-gray-300">
                  Reviewing your code...
                </p>

              </div>
          )}

          {response && (

              <div className="mt-10 bg-black/40 border border-gray-700 rounded-2xl p-8">

                <h2 className="text-3xl font-bold mb-8 text-green-400">
                  AI Analysis Result
                </h2>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-5 mb-8">

                  <div className="bg-blue-600/20 border border-blue-500 rounded-xl p-5">

                    <h3 className="text-lg text-blue-300 mb-2">
                      Code Score
                    </h3>

                    <p className="text-4xl font-bold">
                      {response.score}/100
                    </p>

                  </div>

                  <div className="bg-purple-600/20 border border-purple-500 rounded-xl p-5">

                    <h3 className="text-lg text-purple-300 mb-2">
                      Complexity
                    </h3>

                    <p className="text-3xl font-bold">
                      {response.complexity}
                    </p>

                  </div>

                  <div className="bg-red-600/20 border border-red-500 rounded-xl p-5">

                    <h3 className="text-lg text-red-300 mb-2">
                      Warnings
                    </h3>

                    <p className="text-4xl font-bold">
                      {response.warnings}
                    </p>

                  </div>

                </div>

                <div className="mb-6">

                  <p className="mb-2 text-gray-300">
                    <span className="font-semibold">File:</span>{" "}
                    {response.fileName}
                  </p>

                  <p className="text-gray-300">
                    <span className="font-semibold">Language:</span>{" "}
                    {response.language}
                  </p>

                </div>

                <div className="bg-gray-950 border border-gray-700 rounded-xl p-6">

                  <h3 className="text-xl font-semibold mb-4 text-yellow-300">
                    Detailed Review
                  </h3>

                  <div className="text-gray-300 whitespace-pre-wrap leading-8">

                    {response.review}

                  </div>

                </div>

              </div>
          )}

        </div>

      </div>
  );
}

export default App;