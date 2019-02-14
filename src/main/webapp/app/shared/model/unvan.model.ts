import { ICalisan } from 'app/shared/model/calisan.model';

export interface IUnvan {
    id?: number;
    ad?: string;
    aciklama?: string;
    unvans?: ICalisan[];
}

export class Unvan implements IUnvan {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public unvans?: ICalisan[]) {}
}
