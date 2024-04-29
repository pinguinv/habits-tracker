import axios from 'axios';

const apiPath = "http://localhost:8080/api/habits";

// All habits chart

export async function getChartData(habitsArr){
    let chartDataInFunc = null;

    const doneByCreated = {
        createdDateArr: [],
        numOrHabitsDoneArr: []
    };

    try {
        const doneArr = (await axios.get(`${apiPath}/getLastMonthDoneList`)).data;
        const doneForEachHabit = 
        (await axios.get(`${apiPath}/getLastMonthDoneListForEachHabit`)).data;
        
        doneArr.forEach(done => {
            doneByCreated.createdDateArr.push(done[0].substring(5));
            doneByCreated.numOrHabitsDoneArr.push(done[1]);
        })
        
        chartDataInFunc = {
            labels: doneByCreated.createdDateArr,
            datasets: [
                {
                    label: "All habits",
                    data: doneByCreated.numOrHabitsDoneArr,
                    borderColor: 'rgb(66, 184, 131)',
                    backgroundColor: 'rgba(66, 184, 131, 0.2)',
                    borderWidth: 3,
                    fill: true,
                    tension: 0.3
                }
            ]
        }

        for(let i = 0; i < doneForEachHabit.length; i++){
            
            let borderColor = '';

            // console.log(habitsArr[i].color)
            switch(habitsArr[i].color){
                case "BLUE":
                    borderColor = '90, 142, 255';
                    break;
                case "GREEN":
                    borderColor = '66, 184, 131';
                    break;
                case "PURPLE":
                    borderColor = '169, 133, 214';
                    break;
                case "PINK":
                    borderColor = '255, 166, 230';
                    break;
                case "YELLOW":
                    borderColor = '255, 248, 166';
                    break;
            }

            const newChartDataset = {
                label: habitsArr[i].habitName,
                data: doneForEachHabit[i],
                borderColor: `rgb(${borderColor})`,
                backgroundColor: `rgba(${borderColor}, 0.2)`,
                borderWidth: 3,
                fill: true,
                tension: 0.3
            };
            chartDataInFunc.datasets.push(newChartDataset);
        }


    } catch (error) {
        console.error(error);
    }

    // console.log(chartDataInFunc)
    return chartDataInFunc;
}

// opcje wykresu

export const chartOptions = {
    responsive: true,
    scales: {
        y: {
            beginAtZero: true,
            display: true,
            grid: {
                display: true,
                color: 'rgba(127, 127, 127, 0.5)',

            },
            title: {
                display: true,
                color: 'rgba(222, 222, 222, 0.6)',
                text: 'Num of reps'
            }
        },
        x: {
            display: true,
            grid: {
                display: true,
                color: 'rgba(127, 127, 127, 0.5)',

            },
            title: {
                display: true,
                color: 'rgba(222, 222, 222, 0.6)',
                text: 'Last Month'
            }
        }
    },
    plugins: {
        title: {
            display: true,
            text: 'Number of done repeats per day',
            color: 'rgba(222, 222, 222, 0.6)',
            font: {
                size: 25
            }
        },
        tooltip: {
            displayColors: false
        }
    }
};
