import {Place} from './Place';

export class Trip {
  id?: string;
  description: string;
  departure: Place;
  arrival: Place;
  price: number;
  date: string;
  countSeats: number;
  driver?: string;
}
