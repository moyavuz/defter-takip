import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IPoz } from 'app/shared/model/poz.model';

export interface IBirim {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    carpan?: number;
    malzemeBirims?: IMalzeme[];
    pozBirims?: IPoz[];
}

export class Birim implements IBirim {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public carpan?: number,
        public malzemeBirims?: IMalzeme[],
        public pozBirims?: IPoz[]
    ) {}
}
