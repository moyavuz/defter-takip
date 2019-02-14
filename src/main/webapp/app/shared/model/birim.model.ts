import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IPoz } from 'app/shared/model/poz.model';

export interface IBirim {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    carpan?: number;
    birims?: IMalzeme[];
    birims?: IIscilik[];
    birims?: IPoz[];
}

export class Birim implements IBirim {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public carpan?: number,
        public birims?: IMalzeme[],
        public birims?: IIscilik[],
        public birims?: IPoz[]
    ) {}
}
