const DemoData = {
    resources: [
        {
            id: 'r0',
            name: 'Resource0',
            groupOnly: true,
        },
        {
            id: 'r1',
            name: 'Resource1',
            parentId: 'r0',
        },
        {
            id: 'r2',
            name: 'Resource2',
            parentId: 'r3',
        },
        {
            id: 'r3',
            name: 'Resource3',
            parentId: 'r1',
        },
        {
            id: 'r4',
            name: 'Resource4',
        },
        {
            id: 'r5',
            name: 'Resource5',
        },
        {
            id: 'r6',
            name: 'Resource6',
        },
        {
            id: 'r7',
            name: 'Resource7Resource7Resource7Resource7Resource7',
        }
    ],
    events: [
        {
            id: 1,
            start: '2019-07-11 11:30:00',
            end: '2019-07-11 13:30:00',
            resourceId: 'r1',
            title: 'I am finished',
            bgColor: '#D9D9D9',
            movable: false,
            resizable: false,
            showPopover: true
        },
        {
            id: 8,
            start: '2019-07-11 13:50:00',
            end: '2019-07-12 19:30:00',
            resourceId: 'r1',
            title: 'I am locked',
            bgColor: 'red',
            movable: false,
            resizable: false,
            showPopover: true
        },
        {
            id: 10,
            start: '2017-12-19 17:30:00',
            end: '2017-12-19 23:30:00',
            resourceId: 'r1',
            title: 'R1 has recurring tasks every week on Tuesday, Friday',
            rrule: 'FREQ=WEEKLY;DTSTART=20171219T013000Z;BYDAY=TU,FR',
            bgColor: '#f759ab'
        }
    ]
}

export {DemoData}