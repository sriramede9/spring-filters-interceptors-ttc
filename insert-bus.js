// Run this in mongosh
use test; // or your specific database name

db.buses.drop();

db.buses.insertMany([
  {
    _id: "501",
    routeName: "501 Queen",
    driverName: "John Doe",
    active: true,
    passengers: []
  },
  {
    _id: "320",
    routeName: "320 Yonge",
    driverName: "Jane Smith",
    active: true,
    passengers: []
  }
]);