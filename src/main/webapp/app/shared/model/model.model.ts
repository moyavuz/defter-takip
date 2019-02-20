import { IMarka } from 'app/shared/model/marka.model';

export interface IModel {
    id?: number;
    ad?: string;
    marka?: IMarka;
}

export class Model implements IModel {
    constructor(public id?: number, public ad?: string, public marka?: IMarka) {}
}
