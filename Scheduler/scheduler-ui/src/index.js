import React from 'react';
import ReactDOM from 'react-dom';
import DemoScheduler from './scheduler/DemoScheduler';

class Index extends React.Component {

    constructor() {
        super();
        this.currentMonth = new Date();
    }

    render() {
        return (
            <DemoScheduler/>
        );
    }

}

ReactDOM.render(
    <Index />,
    document.getElementById('root')
);