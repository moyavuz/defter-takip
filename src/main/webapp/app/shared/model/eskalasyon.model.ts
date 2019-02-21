import { Moment } from 'moment';

export interface IEskalasyon {
    id?: number;
    deger?: number;
    tarih?: Moment;
}

export class Eskalasyon implements IEskalasyon {
    constructor(public id?: number, public deger?: number, public tarih?: Moment) {}
}
