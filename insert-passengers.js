// Add 30 passengers to Bus 501 and Bus 320
["501", "320"].forEach(busId => {
    let newPassengers = [];
    for (let i = 1; i <= 30; i++) {
        newPassengers.push({
            name: "Passenger_" + i,
            ticketId: "TKT-" + busId + "-" + i,
            boardingStop: "Union Station",
            boardedAt: new Date()
        });
    }
    
    db.buses.updateOne(
        { _id: busId },
        { $set: { passengers: newPassengers } }
    );
});

// Verification
db.buses.find({}, { routeName: 1, passengerCount: { $size: "$passengers" } });