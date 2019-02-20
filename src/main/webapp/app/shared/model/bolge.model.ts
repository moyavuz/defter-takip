import { IPersonel } from 'app/shared/model/personel.model';
import { IDepartman } from 'app/shared/model/departman.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    bolgeSorumlu?: IPersonel;
    departman?: IDepartman;
}

export class Bolge implements IBolge {
    constructor(
        public id?: number,
        public ad?: string,
        public adres?: string,
        public bolgeSorumlu?: IPersonel,
        public departman?: IDepartman
    ) {}
}
