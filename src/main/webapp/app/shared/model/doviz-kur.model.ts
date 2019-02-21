import { Moment } from 'moment';

export const enum ParaBirimi {
    TL = 'TL',
    USD = 'USD',
    EUR = 'EUR'
}

export interface IDovizKur {
    id?: number;
    paraBirimi?: ParaBirimi;
    deger?: number;
    tarih?: Moment;
}

export class DovizKur implements IDovizKur {
    constructor(public id?: number, public paraBirimi?: ParaBirimi, public deger?: number, public tarih?: Moment) {}
}
