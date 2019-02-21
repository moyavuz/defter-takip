import { IMudurluk } from 'app/shared/model/mudurluk.model';

export interface ISantral {
    id?: number;
    ad?: string;
    mudurluk?: IMudurluk;
}

export class Santral implements ISantral {
    constructor(public id?: number, public ad?: string, public mudurluk?: IMudurluk) {}
}
