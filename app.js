// const apiKey = "5232456521e9b4d69b85e2e0db55c674";
// const apiUrl = "https://api.openweathermap.org/data/2.5/weather?units=metric&q=";

// const PressureInfo = document.querySelector(".pressure")

// async function checkWeather(city) {
//     const response = await fetch(apiUrl + city + `&appid=${apiKey}`);
//     const data = await response.json();
//     const {pressure} =data.main
    
//     document.querySelector(".city").innerHTML = `City name: ${data.name}`;
//     document.querySelector(".temp").innerHTML ="Temperature:"+ Math.round(data.main.temp) + "°C";
//     PressureInfo.innerText = `Pressure: ${pressure} hPa`

//     updateWeatherIcon(data.main.temp);
// }

// function updateWeatherIcon(city) {
// }

// checkWeather("Slough")



// fetch('http://localhost:8081/weather')
//     .then(response => response.json())
//     .then(data => {
//         if (data.length > 0) {
//             const lastData = data[data.length - 1];
//             let weather = document.getElementById('weather');
//             console.log(data);
//             let city_name = lastData.city;
//             let temp = lastData.temperature - 273;
//             let pressure = lastData.pressure;
//             weather.innerHTML += `<p>City: ${city_name}, <br> Temperature: ${temp.toFixed(0)}&#8451;, <br> Humidity: ${pressure}</p>`;
//         } else {
//             console.error('No weather data received');
//         }
//     })
//     .catch(error => {
//         console.error('Error fetching weather data:', error);
//     });



function DataView(data){
    let dataDiv = document.getElementById('weather');
    data.forEach(entry=>{
        let city_name = entry.city;
        let temp = entry.temperature-273;
        let pressure = entry.pressure;
        dataDiv.innerHTML=`<p>City: ${city_name}, <br> Temperature: ${temp.toFixed(0)}&#8451;, <br> Pressure: ${pressure}</p>`
    });
}

const cachedData = localStorage.getItem('weather');
if(cachedData){
    let dataFromStorage =JSON.parse(cachedData);
    DataView(dataFromStorage);
}
else{
    //fetch
    fetch('http://localhost:8081/weather').then(Response=>Response.json())
        .then(data=>{
            localStorage.setItem("weather", JSON.stringify(data));
            DataView(data);
        })
        .catch(Error=> console.log("error:",Error))
}